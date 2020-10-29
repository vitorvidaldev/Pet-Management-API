import { ApiProperty } from "@nestjs/swagger";
import { IsString, IsNotEmpty, IsIn, IsUUID } from "class-validator";
import { NoteFrequency } from "../note-frequency.enum";

export class CreateNoteDto {
    @ApiProperty({ description: 'Tipo da nota', example: 'Dar banho' })
    @IsString()
    @IsNotEmpty()
    noteType: string;

    @ApiProperty({ description: 'Título da nota', example: 'Comprar ração' })
    @IsString()
    @IsNotEmpty()
    title: string;

    @ApiProperty({ description: 'Descrição da nota', example: 'Ir no mercado comprar ração' })
    @IsString()
    @IsNotEmpty()
    description: string;

    @ApiProperty({ description: 'Data da nota', example: '02/10/2020' })
    @IsString()
    @IsNotEmpty()
    noteDate: string;

    @ApiProperty({ description: 'Repetição da nota', example: 'Dia' })
    @IsString()
    @IsNotEmpty()
    @IsIn([NoteFrequency.DAY, NoteFrequency.WEEK, NoteFrequency.MONTH, NoteFrequency.NONE])
    frequency: NoteFrequency;

    @ApiProperty({ description: 'id do animal.', example: 'c58080ec-991a-459a-98db-ac5604375e28' })
    @IsString()
    @IsNotEmpty()
    @IsUUID()
    animalId: string;
}