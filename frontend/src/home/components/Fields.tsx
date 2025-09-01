import {Box, IconButton, Table, TableBody, TableCell, TableRow, Tooltip, Typography} from "@mui/material";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import {Field} from "./Field.tsx";
import type {FieldType} from "../../types.ts";

type FieldsComponentProps = {
    fields: FieldType[],
    onRemove: (index: number) => void
    onUpdate: (updatedField: FieldType, index: number) => void
    onAdd: () => void
}
export const Fields = ({fields, onRemove, onUpdate, onAdd}: FieldsComponentProps) => {
    return <Box>

        <Typography sx={{m: 0, p: 0}}>Fields</Typography>

        <Table size="small" sx={{width: "auto"}}>
            <TableBody>
                {fields.map((field, index) =>
                    <Field
                        key={index}
                        field={field}
                        index={index}
                        onRemove={onRemove}
                        onUpdate={(updatedField) => {
                            onUpdate(updatedField, index)
                        }}/>
                )}

                <TableRow>
                    <TableCell sx={{p: 0, border: 0}}>
                        <Tooltip title="Add another field" placement="bottom-end" arrow>
                            <IconButton onClick={onAdd}><AddCircleOutlineIcon/></IconButton>
                        </Tooltip>
                    </TableCell>
                </TableRow>

            </TableBody>

        </Table>

    </Box>
}