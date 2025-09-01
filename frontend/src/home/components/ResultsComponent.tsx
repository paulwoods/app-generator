import type {GenerateResults} from "../../types.ts";
import {Paper, Tab, Tabs} from "@mui/material";
import {type SyntheticEvent} from "react";
import {CodeComponent} from "./CodeComponent.tsx";
import {useLocalStorage} from "usehooks-ts";

type ResultsComponentProps = {
    results: GenerateResults
}
export const ResultsComponent = ({results}: ResultsComponentProps) => {

    const [tab, setTab] = useLocalStorage("results-tab", 0);

    const handleChangeTab = (_event: SyntheticEvent, newValue: number) => {
        setTab(newValue);
    };

    return <Paper sx={{mb: "10em"}}>

        <Tabs value={tab} onChange={handleChangeTab}>
            {results.codes.map(code =>
                <Tab key={code.name} label={code.name}/>
            )}
        </Tabs>

        {results.codes[tab] && <CodeComponent code={results.codes[tab]}/>}

    </Paper>

}