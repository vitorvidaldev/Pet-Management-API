import { ApiPropertyOptional } from "@nestjs/swagger";
import { IsNotEmpty, IsOptional, IsString } from "class-validator";

export class GetVaccinesDto {
  @ApiPropertyOptional({ description: "animal species" })
  @IsString()
  @IsOptional()
  @IsNotEmpty()
  species!: string;

  @ApiPropertyOptional({ description: "animal breed" })
  @IsString()
  @IsOptional()
  @IsNotEmpty()
  breed!: string;
}
