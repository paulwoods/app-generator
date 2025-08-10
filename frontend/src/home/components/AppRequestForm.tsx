import {useLocalStorage} from "usehooks-ts";
import type {AppRequest, Field} from "../../types.ts";
import {Box, Button, Paper, TextField, Typography} from "@mui/material";
import {produce} from "immer";
import {FieldsComponent} from "./FieldsComponent.tsx";

type AppRequestFormProps = {
    storage: string
    onGenerate: (appRequest: AppRequest) => void
}
export const AppRequestForm = ({storage, onGenerate}: AppRequestFormProps) => {

    const [appRequest, setAppRequest] = useLocalStorage<AppRequest>(storage,
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

    const updateField = (newField: Field, index: number) => {
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

    return <Paper sx={{p: 3, mt: 3, mb: 3}}>

        <Typography sx={{m: 0, p: 0, mb: 3}}>App Generator Form</Typography>

        <Box sx={{display: "flex", flexDirection: "column", alignItems: "flex-start", gap: 3, width: "100%"}}>

            <TextField
                size="small"
                fullWidth
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

            <FieldsComponent
                fields={appRequest.fields}
                onRemove={handleRemoveField}
                onUpdate={updateField}
                onAdd={handleAddField}
            />

            <Button size="small" variant="contained" onClick={handleGenerate} disabled={!canGenerate}>Generate</Button>

        </Box>

    </Paper>
}
