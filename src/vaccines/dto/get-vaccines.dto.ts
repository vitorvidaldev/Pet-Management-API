import { ApiPropertyOptional } from "@nestjs/swagger";
import { IsNotEmpty, IsOptional, IsString } from "class-validator";

export class GetVaccinesDto {
    @ApiPropertyOptional({ description: 'Espécie do animal' })
    @IsString()
    @IsOptional()
    @IsNotEmpty()
    species: string;

    @ApiPropertyOptional({ description: 'Raça do animal' })
    @IsString()
    @IsOptional()
    @IsNotEmpty()
    race: string;
}