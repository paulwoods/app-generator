import {useLocalStorage} from "usehooks-ts";
import type {AppRequestType, FieldType} from "../../types.ts";
import {Box, Button, TextField} from "@mui/material";
import {produce} from "immer";
import {Fields} from "./Fields.tsx";

type AppRequestFormProps = {
    storage: string
    onGenerate: (appRequest: AppRequestType) => void
}
export const RequestForm = ({storage, onGenerate}: AppRequestFormProps) => {

    const [appRequest, setAppRequest] = useLocalStorage<AppRequestType>(storage,
        {
            entity: '',
            pkg: '',
            fields: [
                {
                    name: "name",
                    type: "String",
                    id: false,
                    minSize: null,
                    maxSize: null,
                    nullable: false
                },
                {
                    name: "email",
                    type: "String",
                    id: false,
                    minSize: null,
                    maxSize: null,
                    nullable: false
                }
            ]
        }
    )

    const handleGenerate = () => {
        onGenerate(appRequest);
    };

    const updateField = (newField: FieldType, index: number) => {
        setAppRequest(produce(appRequest, draft => {
            draft.fields[index] = newField;
        }));
    };

    const handleAddField = () => {
        setAppRequest(produce(appRequest, draft => {
            draft.fields = [...draft.fields, {
                name: "",
                type: "",
                id: false,
                maxSize: null,
                minSize: null,
                nullable: false
            }];
        }));
    };

    const handleRemoveField = (index: number) => {
        setAppRequest(produce(appRequest, draft => {
            draft.fields.splice(index, 1);
        }));
    };

    let canGenerate = appRequest.entity && appRequest.pkg;
    appRequest.fields.forEach(field => {
        canGenerate = canGenerate && field.name && field.type;
    });

    return <Box>

        {/*<Typography sx={{m: 0, p: 0, mb: 6}} variant="h4">App Generator Form</Typography>*/}

        <Box sx={{display: "flex", flexDirection: "column", alignItems: "flex-start", gap: 3, width: "100%"}}>

            <Box sx={{display: "flex", gap: 3, width: "100%"}}>

                <TextField
                    size="small"
                    sx={{width: "30em"}}
                    label="Entity Name"
                    required
                    slotProps={{inputLabel: {shrink: true}}}
                    value={appRequest.entity}
                    onChange={(e) => setAppRequest({...appRequest, entity: e.target.value})}
                />

                <TextField
                    size="small"
                    fullWidth
                    label="Package"
                    required
                    slotProps={{inputLabel: {shrink: true}}}
                    value={appRequest.pkg}
                    onChange={(e) => setAppRequest({...appRequest, pkg: e.target.value})}
                />

                <div>
                    <Button variant="contained" onClick={handleGenerate}
                            disabled={!canGenerate}>Generate</Button>
                </div>

            </Box>

            <Fields
                fields={appRequest.fields}
                onRemove={handleRemoveField}
                onUpdate={updateField}
                onAdd={handleAddField}
            />

        </Box>

    </Box>
}
