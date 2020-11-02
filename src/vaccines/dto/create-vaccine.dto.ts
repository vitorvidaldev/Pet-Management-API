import { ApiProperty } from "@nestjs/swagger";
import { IsNotEmpty, IsString } from "class-validator";

export class CreateVaccineDto {
    @ApiProperty({ description: 'Nome da vacina', example: 'nome da vacina' })
    @IsString()
    @IsNotEmpty()
    name: string;

    @ApiProperty({ description: 'Espécie do animal', example: 'especie do animal' })
    @IsString()
    @IsNotEmpty()
    species: string;

    @ApiProperty({ description: 'Raça do animal', example: 'Raça do animal' })
    @IsString()
    @IsNotEmpty()
    race: string;

    @ApiProperty({ description: 'Meses após nascimento', example: 'Meses após nascimento' })
    @IsNotEmpty()
    monthsAfterBirth: string;
}