import {IconButton, TableCell, TableRow, TextField} from "@mui/material";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import type {Field} from "../../types.ts";

type FieldComponentProps = {
    field: Field
    index: number
    onRemove: (index: number) => void
    onUpdate: (fieldName: string, index: number, value: string) => void
}
export const FieldComponent = ({field, index, onRemove, onUpdate}: FieldComponentProps) => <div>

    <TableRow>
        <TableCell sx={{p: 0, border: 0}}>
            <IconButton onClick={() => onRemove(index)}><RemoveCircleOutlineIcon/></IconButton>
        </TableCell>
        <TableCell sx={{p: 0, border: 0}}>
            <TextField
                size="small"
                fullWidth
                label="name"
                required
                slotProps={{inputLabel: {shrink: true}}}
                value={field.name}
                onChange={(e) => onUpdate("name", index, e.target.value)}
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
                onChange={(e) => onUpdate("type", index, e.target.value)}
            />
        </TableCell>
        <TableCell sx={{border: 0}}>
            <TextField
                size="small"
                fullWidth
                label="min size"
                slotProps={{inputLabel: {shrink: true}}}
                value={field.minSize || ""}
                onChange={(e) => onUpdate("minSize", index, e.target.value)}
            />
        </TableCell>
        <TableCell sx={{border: 0}}>
            <TextField
                size="small"
                fullWidth
                label="max size"
                slotProps={{inputLabel: {shrink: true}}}
                value={field.maxSize || ""}
                onChange={(e) => onUpdate("maxSize", index, e.target.value)}
            />
        </TableCell>
    </TableRow>

</div>