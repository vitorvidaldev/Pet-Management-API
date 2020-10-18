import { ApiProperty } from "@nestjs/swagger";
import { IsString, IsNotEmpty, IsIn, IsUUID } from "class-validator";
import { AnimalSpecies } from "../animal-species.enum";

export class CreateAnimalDto {
    @ApiProperty({ description: 'Nome do animal', example: 'Omar' })
    @IsString()
    @IsNotEmpty()
    name: string;

    @ApiProperty({ description: 'Data de nascimento', example: '2/10/2020' })
    @IsString()
    @IsNotEmpty()
    birthDate: string;

    @ApiProperty({ description: 'Espécies cadastradas', example: 'Cachorro' })
    @IsString()
    @IsNotEmpty()
    @IsIn([AnimalSpecies.CACHORRO, AnimalSpecies.GATO, AnimalSpecies.NAOINFORMAR, AnimalSpecies.OUTRO])
    species: AnimalSpecies;

    @ApiProperty({ description: 'Raça do animal', example: 'teste' })
    @IsString()
    @IsNotEmpty()
    race: string;

    @ApiProperty({ description: 'id do usuário', example: 'b0bf8219-c00b-4838-853f-0d0c6834850c' })
    @IsString()
    @IsNotEmpty()
    @IsUUID()
    userId: string;
}