import {useLocalStorage} from "usehooks-ts";
import type {AppRequest} from "../../types.ts";
import {
    Box,
    Button,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableRow,
    TextField,
    Typography
} from "@mui/material";
import {produce} from "immer";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';


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
                },
                {
                    name: "email",
                    type: "String",
                }
            ]
        }
    )

    const handleGenerate = () => {
        onGenerate(appRequest);
    };

    let canGenerate = appRequest.entity && appRequest.pkg;
    appRequest.fields.forEach(field => {
        canGenerate = canGenerate && field.name && field.type;
    });


    const updateField = (fieldName: string, index: number, value: string) => {
        setAppRequest(produce(appRequest, draft => {
            if (fieldName == "name") {
                draft.fields[index].name = value;
            } else if (fieldName == "type") {
                draft.fields[index].type = value;
            } else if (fieldName == "minSize") {
                draft.fields[index].minSize = parseInt(value, 10);
            } else if (fieldName == "maxSize") {
                draft.fields[index].maxSize = parseInt(value, 10);
            }
        }));
    };

    const handleAdd = () => {
        setAppRequest(produce(appRequest, draft => {
            draft.fields = [...draft.fields, {name: "", type: ""}];
        }));
    };

    const handleRemove = (index: number) => {
        setAppRequest(produce(appRequest, draft => {
            draft.fields.splice(index, 1);
        }));
    };

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

            <Typography sx={{m: 0, p: 0}}>Fields</Typography>

            <Table size="small">
                <TableBody>
                    {appRequest.fields?.map((field, index) =>
                        <TableRow key={index}>
                            <TableCell sx={{p: 0, border: 0}}>
                                <IconButton onClick={() => handleRemove(index)}><RemoveCircleOutlineIcon/></IconButton>
                            </TableCell>
                            <TableCell sx={{p: 0, border: 0}}>
                                <TextField
                                    size="small"
                                    fullWidth
                                    label="name"
                                    required
                                    slotProps={{inputLabel: {shrink: true}}}
                                    value={field.name}
                                    onChange={(e) => updateField("name", index, e.target.value)}
                                />
                            </TableCell>
                            <TableCell sx={{border: 0}}>
                                <TextField
                                    size="small"
                                    fullWidth
                                    label="type"
                                    required
                                    slotProps={{inputLabel: {shrink: true}}}
                                    value={field.type}
                                    onChange={(e) => updateField("type", index, e.target.value)}
                                />
                            </TableCell>
                            <TableCell sx={{border: 0}}>
                                <TextField
                                    size="small"
                                    fullWidth
                                    label="min size"
                                    slotProps={{inputLabel: {shrink: true}}}
                                    value={field.minSize || ""}
                                    onChange={(e) => updateField("minSize", index, e.target.value)}
                                />
                            </TableCell>
                            <TableCell sx={{border: 0}}>
                                <TextField
                                    size="small"
                                    fullWidth
                                    label="max size"
                                    slotProps={{inputLabel: {shrink: true}}}
                                    value={field.maxSize || ""}
                                    onChange={(e) => updateField("maxSize", index, e.target.value)}
                                />
                            </TableCell>
                        </TableRow>
                    )}
                    <TableRow>
                        <TableCell sx={{p: 0, border: 0}}>
                            <IconButton onClick={handleAdd}><AddCircleOutlineIcon/></IconButton>
                        </TableCell>
                    </TableRow>

                </TableBody>

            </Table>

            <Button size="small" variant="contained" onClick={handleGenerate} disabled={!canGenerate}>Generate</Button>

        </Box>

    </Paper>
}