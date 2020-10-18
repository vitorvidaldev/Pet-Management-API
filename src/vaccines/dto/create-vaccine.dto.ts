import { IsNotEmpty, IsNumber, IsString } from "class-validator";

export class CreateVaccineDto {
    @IsString()
    @IsNotEmpty()
    name: string;

    @IsString()
    @IsNotEmpty()
    species: string;

    @IsString()
    @IsNotEmpty()
    race: string;

    @IsNumber()
    @IsNotEmpty()
    monthsAfterBirth: number;
}