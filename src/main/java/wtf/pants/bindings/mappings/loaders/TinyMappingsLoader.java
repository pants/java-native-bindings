package wtf.pants.bindings.mappings.loaders;

import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.mappings.MemberMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TinyMappingsLoader implements MappingLoader {

    public static ClassMap parseClassMap(final String[] line) {
        final String obfuscatedName = line[1];
        final String intermediateName = line[2];
        final String cleanName = line[3];

        return new ClassMap(obfuscatedName, intermediateName, cleanName);
    }

    public static MemberMap parseMember(String[] line) {
        final String obfMethodSig = line[1];
        final String obfuscatedName = line[2];
        final String intermediateName = line[3];
        final String cleanName = line[4];

        return new MemberMap(line[0].equals("m"), obfMethodSig, obfuscatedName, intermediateName, cleanName);
    }

    private List<String> readFile(File file) {
        try {
            final Path path = file.toPath();
            return Files.readAllLines(path);
        } catch (IOException exception) {
            System.out.println("Failed to read mappings file..?");
            exception.printStackTrace();
        }

        return new ArrayList<>();
    }

    private void parseLine(final String[] delimited, final List<ClassMap> maps, final LastMapped last) {
        //Tiny mapping lines are prefixed with the line type.
        switch (delimited[0]) {
            case "c":
                last.classMap = parseClassMap(delimited);
                maps.add(last.classMap);
                break;
            case "m":
            case "f":
                if (last.classMap != null) {
                    last.member = parseMember(delimited);
                    last.classMap.getMembers().add(last.member);
                } else {
                    System.out.println("Attempted to add class member when no class was available.");
                }
                break;
            case "p":
                last.member.getParams().add(delimited[4]);
                break;
            case "tiny":
                break;
            default:
                System.out.println("Unknown key: " + delimited[0]);
        }
    }

    protected List<ClassMap> iterateLines(final List<String> lines){
        final List<ClassMap> classMaps = new ArrayList<>();
        final LastMapped lastMapped = new LastMapped();

        for (final String line : lines) {
            //Methods, fields, and params start with a tab character, so we trim it here.
            String trimmedLine = line;
            while (trimmedLine.startsWith("\t")) {
                trimmedLine = trimmedLine.substring(1); //substring 1 to get rid of first tab character
            }

            final String[] delimited = trimmedLine.split("\t");

            if (line.startsWith("\t") && delimited[0].equals("c")) {
                continue;
            }

            parseLine(delimited, classMaps, lastMapped);
        }

        return classMaps;
    }

    @Override
    public List<ClassMap> loadMappingFiles(File file) {
        final List<String> lines = readFile(file);
        return iterateLines(lines);
    }

    private static class LastMapped {
        public ClassMap classMap = null;
        public MemberMap member = null;
    }
}
