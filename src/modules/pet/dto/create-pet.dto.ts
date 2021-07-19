import { ApiProperty } from "@nestjs/swagger";
import { IsString, IsNotEmpty, IsIn, IsUUID } from "class-validator";
import { Species } from "../enum/species.enum";

export class CreatePetDto {
  @ApiProperty({ description: "Pet Name", example: "Pet Name" })
  @IsString()
  @IsNotEmpty()
  name!: string;

  @ApiProperty({ description: "Birth date", example: "Birth date" })
  @IsNotEmpty()
  birthDate!: string;

  @ApiProperty({
    description: "Species",
    example: "Species"
  })
  @IsString()
  @IsNotEmpty()
  @IsIn([Species.DOG, Species.CAT, Species.OTHER])
  species!: Species;

  @ApiProperty({ description: "Breed", example: "Breed" })
  @IsString()
  @IsNotEmpty()
  breed!: string;

  @ApiProperty({ description: "User id", example: "User id" })
  @IsString()
  @IsNotEmpty()
  @IsUUID()
  userId!: string;
}
