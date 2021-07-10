import { ApiProperty } from "@nestjs/swagger";
import { IsString, IsNotEmpty, IsIn, IsUUID } from "class-validator";
import { NoteFrequency } from "../note-frequency.enum";

export class CreateNoteDto {
  @ApiProperty({ description: "Note type", example: "Note type" })
  @IsString()
  @IsNotEmpty()
  noteType!: string;

  @ApiProperty({ description: "Note title", example: "Note title" })
  @IsString()
  @IsNotEmpty()
  title!: string;

  @ApiProperty({
    description: "Note description",
    example: "Note description"
  })
  @IsString()
  @IsNotEmpty()
  description!: string;

  @ApiProperty({ description: "Trigger date", example: "02/10/2020" })
  @IsString()
  @IsNotEmpty()
  noteDate!: Date;

  @ApiProperty({ description: "Note frequency", example: "Daily" })
  @IsString()
  @IsNotEmpty()
  @IsIn([
    NoteFrequency.DAY,
    NoteFrequency.WEEK,
    NoteFrequency.MONTH,
    NoteFrequency.NONE
  ])
  frequency!: string;

  @ApiProperty({ description: "Animal id.", example: "animal id" })
  @IsString()
  @IsNotEmpty()
  @IsUUID()
  animalId!: string;
}
