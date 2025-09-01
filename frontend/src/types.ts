export type FieldType = {
    name: string;
    type: string;
    id: boolean;
    minSize: number | null;
    maxSize: number | null;
    nullable: boolean;
}

export type AppRequestType = {
    entity: string;
    pkg: string;
    fields: FieldType[];
}

export type GenerateResultsType = {
    codes: CodeType[]
}

export type CodeType = {
    name: string;
    fileName: string;
    content: string;
}

export type FileBuilderNameType = {
    name: string
}

