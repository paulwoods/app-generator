export type Field = {
    name: string;
    type: string;
    id: boolean;
    minSize: number | null;
    maxSize: number | null;
    nullable: boolean;
}

export type AppRequest = {
    entity: string;
    pkg: string;
    fields: Field[];
}

export type GenerateResults = {
    codes: Code[]
}

export type Code = {
    name: string;
    fileName: string;
    content: string;
}
