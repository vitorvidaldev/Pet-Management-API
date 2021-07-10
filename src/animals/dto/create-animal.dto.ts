import { ApiProperty } from "@nestjs/swagger";
import { IsString, IsNotEmpty, IsIn, IsUUID } from "class-validator";
import { AnimalSpecies } from "../enum/animal-species.enum";

export class CreateAnimalDto {
  @ApiProperty({ description: "animal name", example: "Omar" })
  @IsString()
  @IsNotEmpty()
  name!: string;

  @ApiProperty({ description: "Date of birth", example: "2/10/2020" })
  @IsNotEmpty()
  birthDate!: string;

  @ApiProperty({
    description: "Registered species",
    example: "animal species"
  })
  @IsString()
  @IsNotEmpty()
  @IsIn([
    AnimalSpecies.DOG,
    AnimalSpecies.CAT,
    AnimalSpecies.OTHER
  ])
  species!: AnimalSpecies;

  @ApiProperty({ description: "Breed", example: "Breed" })
  @IsString()
  @IsNotEmpty()
  breed!: string;

  @ApiProperty({ description: "user id", example: "user id" })
  @IsString()
  @IsNotEmpty()
  @IsUUID()
  userId!: string;
}
