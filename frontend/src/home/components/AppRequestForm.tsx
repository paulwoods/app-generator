import {useLocalStorage} from "usehooks-ts";
import type {AppRequest} from "../../types.ts";
import {Box, Button, TextField} from "@mui/material";

type AppRequestFormProps = {
    storage: string
    onGenerate: (appRequest: AppRequest) => void
}

export const AppRequestForm = ({storage, onGenerate}: AppRequestFormProps) => {

    const [appRequest, setAppRequest] = useLocalStorage<AppRequest>(storage,
        {
            entity: '',
            pkg: ''
        }
    )

    const handleGenerate = () => {
        onGenerate(appRequest);
    };

    const canGenerate = appRequest.entity && appRequest.pkg;

    return <Box sx={{display: "flex", flexDirection: "column", alignItems: "flex-start", gap: 3, width: "100%"}}>

        <TextField
            size="small"
            fullWidth
            label="Entity Name"
            value={appRequest.entity}
            onChange={(e) => setAppRequest({...appRequest, entity: e.target.value})}
        />

        <TextField
            size="small"
            fullWidth
            label="Package"
            value={appRequest.pkg}
            onChange={(e) => setAppRequest({...appRequest, pkg: e.target.value})}
        />

        <Button size="small" variant="contained" onClick={handleGenerate} disabled={!canGenerate}>Generate</Button>

    </Box>
}