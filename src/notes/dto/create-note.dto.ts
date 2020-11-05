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
    noteDate: Date;

    @ApiProperty({ description: 'Repetição da nota', example: 'Dia' })
    @IsString()
    @IsNotEmpty()
    @IsIn([NoteFrequency.DAY, NoteFrequency.WEEK, NoteFrequency.MONTH, NoteFrequency.NONE])
    frequency: string;

    @ApiProperty({ description: 'id do animal.', example: 'id de um animal' })
    @IsString()
    @IsNotEmpty()
    @IsUUID()
    animalId: string;
}