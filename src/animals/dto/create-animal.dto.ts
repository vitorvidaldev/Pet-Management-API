import { ApiProperty } from "@nestjs/swagger";
import { IsString, IsNotEmpty, IsIn, IsUUID } from "class-validator";
import { AnimalSpecies } from "../enum/animal-species.enum";

export class CreateAnimalDto {
  @ApiProperty({ description: "Nome do animal", example: "Omar" })
  @IsString()
  @IsNotEmpty()
  name: string;

  @ApiProperty({ description: "Data de nascimento", example: "2/10/2020" })
  @IsNotEmpty()
  birthDate: string;

  @ApiProperty({
    description: "Espécies cadastradas",
    example: "Espécie do animal",
  })
  @IsString()
  @IsNotEmpty()
  @IsIn([
    AnimalSpecies.CACHORRO,
    AnimalSpecies.GATO,
    AnimalSpecies.NAO_INFORMAR,
    AnimalSpecies.OUTRO,
  ])
  species: AnimalSpecies;

  @ApiProperty({ description: "Raça do animal", example: "Raça de um animal" })
  @IsString()
  @IsNotEmpty()
  breed: string;

  @ApiProperty({ description: "id de um usuário", example: "id de um usuário" })
  @IsString()
  @IsNotEmpty()
  @IsUUID()
  userId: string;
}
