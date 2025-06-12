package org.yaml.snakeyaml.tokens;

public enum Token$ID {
    Alias("<alias>"),
    Anchor("<anchor>"),
    BlockEnd("<block end>"),
    BlockEntry("-"),
    BlockMappingStart("<block mapping start>"),
    BlockSequenceStart("<block sequence start>"),
    Directive("<directive>"),
    DocumentEnd("<document end>"),
    DocumentStart("<document start>"),
    FlowEntry(","),
    FlowMappingEnd("}"),
    FlowMappingStart("{"),
    FlowSequenceEnd("]"),
    FlowSequenceStart("["),
    Key("?"),
    Scalar("<scalar>"),
    StreamEnd("<stream end>"),
    StreamStart("<stream start>"),
    Tag("<tag>"),
    Value(":"),
    Whitespace("<whitespace>"),
    Comment("#"),
    Error("<error>");

    private final String description;

    private Token$ID(String s) {
        this.description = s;
    }

    public String toString() {
        return this.description;
    }
}
