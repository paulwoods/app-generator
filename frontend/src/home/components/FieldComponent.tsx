import {Checkbox, FormControlLabel, IconButton, type SxProps, TableCell, TableRow, TextField} from "@mui/material";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import type {Field} from "../../types.ts";

type FieldComponentProps = {
    field: Field
    index: number
    onRemove: (index: number) => void
    onUpdate: (updatedField: Field) => void
}
export const FieldComponent = ({field, index, onRemove, onUpdate}: FieldComponentProps) => {

    const sx1: SxProps = {border: 0};
    const sx2: SxProps = {...sx1, p: 0}

    return <>

        <TableRow hover>

            <TableCell sx={sx2}>
                <IconButton onClick={() => onRemove(index)}><RemoveCircleOutlineIcon/></IconButton>
            </TableCell>

            <TableCell sx={sx2}>
                <TextField
                    size="small"
                    fullWidth
                    label="name"
                    required
                    slotProps={{inputLabel: {shrink: true}}}
                    value={field.name}
                    onChange={(e) => onUpdate({...field, name: e.target.value})}
                />
            </TableCell>

            <TableCell sx={sx1}>
                <TextField
                    size="small"
                    fullWidth
                    label="type"
                    required
                    slotProps={{inputLabel: {shrink: true}}}
                    value={field.type}
                    onChange={(e) => onUpdate({...field, type: e.target.value})}
                />
            </TableCell>

            <TableCell sx={sx1}>
                <TextField
                    size="small"
                    fullWidth
                    label="min size"
                    slotProps={{inputLabel: {shrink: true}}}
                    value={field.minSize || ""}
                    onChange={(e) => onUpdate({...field, minSize: parseInt(e.target.value, 10)})}
                />
            </TableCell>

            <TableCell sx={sx1}>
                <TextField
                    size="small"
                    fullWidth
                    label="max size"
                    slotProps={{inputLabel: {shrink: true}}}
                    value={field.maxSize || ""}
                    onChange={(e) => onUpdate({...field, maxSize: parseInt(e.target.value, 10)})}
                />
            </TableCell>

            <TableCell sx={sx1}>

                <FormControlLabel control={<Checkbox
                    checked={field.id}
                    onChange={e => onUpdate({...field, id: e.target.checked})}
                />} label="@Id"/>

            </TableCell>

            <TableCell sx={sx1}>

                <FormControlLabel control={<Checkbox
                    checked={field.nullable}
                    onChange={e => onUpdate({...field, nullable: e.target.checked})}
                />} label="Nullable"/>

            </TableCell>

        </TableRow>

    </>

}