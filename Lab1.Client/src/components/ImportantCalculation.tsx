import {Box, Button, Modal, TextField, Typography} from "@mui/material";
import {useState} from "react";
import {getCalculationResult, ResultResponse, startCalculation} from "../api/calculationApi.ts";

interface ImportantCalculationProps {
    isOpen: boolean;
    onClose: () => void;
}


const ImaportantCalculation = ({isOpen, onClose}: ImportantCalculationProps) => {
    const [inputValue, setInputValue] = useState<number>();
    const [resultValue, setResultValue] = useState<string>();

    const handleCalculation = async () => {
        if (inputValue != null) {
            const res = await startCalculation(inputValue);
            console.log(res);
        }
    }

    const handleShowResult = async () => {
        const res: ResultResponse = await getCalculationResult();
        console.log(res);
        if (res.status === "completed")
            setResultValue(res.result.toString())
        else
            setResultValue("Calculation still loading");
    }

    return (
        <Modal open={isOpen} onClose={onClose}>
            <Box sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                width: 400,
                bgcolor: 'background.paper',
                boxShadow: 24,
                p: 4,
            }}>
                <TextField
                    fullWidth
                    type="number"
                    value={inputValue}
                    onChange={(e) => setInputValue(Number(e.target.value))}
                    sx={{ mb: 2 }}
                />
                <Button
                    variant="contained"
                    onClick={handleCalculation}
                    disabled={!inputValue}
                >
                    Calculate
                </Button>
                <Button
                    variant="contained"
                    onClick={handleShowResult}
                >
                    Show Result
                </Button>
                <Typography variant="body2" component="div">{resultValue}</Typography>
            </Box>
        </Modal>
    );
}

export default ImaportantCalculation;
