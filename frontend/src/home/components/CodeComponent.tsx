import type {CodeType} from "../../types.ts";
import {Box, Typography} from "@mui/material";

type CodeComponentProps = {
    code: CodeType
}
export const CodeComponent = ({code}: CodeComponentProps) => {

    return <Box sx={{p: 3, pt: 1}}>
        <Typography variant="h6" style={{marginBottom: "2em"}}>{code.fileName}</Typography>
        <pre>{code.content}</pre>
    </Box>
}
