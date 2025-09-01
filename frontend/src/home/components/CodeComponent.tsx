import type {Code} from "../../types.ts";
import {Box} from "@mui/material";

type CodeComponentProps = {
    code: Code
}
export const CodeComponent = ({code}: CodeComponentProps) => {

    return <Box sx={{p: 3}}>
        <h4 style={{marginBottom: "2em", display: "flex", justifyContent: "center"}}>{code.fileName}</h4>
        <pre>{code.content}</pre>
    </Box>
}
