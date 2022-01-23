import { ApiProperty } from '@nestjs/swagger';
import { IsString, IsNotEmpty, IsIn, IsUUID } from 'class-validator';
import { NoteFrequency } from '../note-frequency.enum';

export class CreateNoteDto {
  @ApiProperty({ description: 'Type', example: 'Type' })
  @IsString()
  @IsNotEmpty()
  type: string;

  @ApiProperty({ description: 'Title', example: 'Title' })
  @IsString()
  @IsNotEmpty()
  title: string;

  @ApiProperty({
    description: 'Description',
    example: 'Description',
  })
  @IsString()
  @IsNotEmpty()
  description: string;

  @ApiProperty({ description: 'Trigger date', example: '02/10/2020' })
  @IsString()
  @IsNotEmpty()
  noteDate: Date;

  @ApiProperty({ description: 'Frequency', example: 'Daily' })
  @IsString()
  @IsNotEmpty()
  @IsIn([
    NoteFrequency.DAY,
    NoteFrequency.WEEK,
    NoteFrequency.MONTH,
    NoteFrequency.NONE,
  ])
  frequency: string;

  @ApiProperty({ description: 'id', example: 'id' })
  @IsString()
  @IsNotEmpty()
  @IsUUID()
  petId: string;
}
