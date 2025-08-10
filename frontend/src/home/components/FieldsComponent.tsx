import {Box, IconButton, Table, TableBody, TableCell, TableRow, Typography} from "@mui/material";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import {FieldComponent} from "./FieldComponent.tsx";
import type {Field} from "../../types.ts";

type FieldsComponentProps = {
    fields: Field[],
    onRemove: (index: number) => void
    onUpdate: (fieldName: string, index: number, value: string) => void
    onAdd: () => void
}
export const FieldsComponent = ({fields, onRemove, onUpdate, onAdd}: FieldsComponentProps) => {
    return <Box>

        <Typography sx={{m: 0, p: 0}}>Fields</Typography>

        <Table size="small">
            <TableBody>
                {fields.map((field, index) =>
                    <FieldComponent key={index} field={field} index={index} onRemove={onRemove} onUpdate={onUpdate}/>
                )}
                <TableRow>
                    <TableCell sx={{p: 0, border: 0}}>
                        <IconButton onClick={onAdd}><AddCircleOutlineIcon/></IconButton>
                    </TableCell>
                </TableRow>

            </TableBody>

        </Table>

    </Box>
}