import { ApiPropertyOptional } from '@nestjs/swagger';
import { IsNotEmpty, IsOptional, IsString } from 'class-validator';

export class GetVaccinesDto {
  @ApiPropertyOptional({ description: 'Pet species' })
  @IsString()
  @IsOptional()
  @IsNotEmpty()
  species: string;

  @ApiPropertyOptional({ description: 'Pet breed' })
  @IsString()
  @IsOptional()
  @IsNotEmpty()
  breed: string;
}
