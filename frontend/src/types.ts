export type FieldName = "name" | "type" | "minSize" | "maxSize";

export type Field = {
    name: string;
    type: string;
    id: boolean;
    minSize?: number | null;
    maxSize?: number | null;
}

export type AppRequest = {
    entity: string;
    pkg: string;
    fields: Field[];
}
