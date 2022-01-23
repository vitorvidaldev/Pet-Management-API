import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';

export class CreateVaccineDto {
  @ApiProperty({ description: 'Vaccine name', example: 'vaccine name' })
  @IsString()
  @IsNotEmpty()
  name: string;

  @ApiProperty({
    description: 'animal species',
    example: 'animal species',
  })
  @IsString()
  @IsNotEmpty()
  species: string;

  @ApiProperty({ description: 'animal breed', example: 'animal breed' })
  @IsString()
  @IsNotEmpty()
  breed: string;

  @ApiProperty({
    description: 'Months after birth',
    example: 'Months after birth',
  })
  @IsNotEmpty()
  monthsAfterBirth: string;
}
