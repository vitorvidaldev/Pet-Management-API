import {
  Controller,
  Post,
  Body,
  ValidationPipe,
  UsePipes,
  Get,
  Param,
  Delete,
  ParseUUIDPipe,
  UseGuards
} from "@nestjs/common";
import { AnimalsService } from "./animals.service";
import { CreateAnimalDto } from "./dto/create-animal.dto";
import { Animal } from "./animal.entity";
import {
  ApiBearerAuth,
  ApiOperation,
  ApiParam,
  ApiResponse,
  ApiTags
} from "@nestjs/swagger";
import { AuthGuard } from "@nestjs/passport";

@ApiTags("Animais")
@ApiResponse({ status: 401, description: "Unauthorized" })
@Controller("animals")
@UseGuards(AuthGuard())
export class AnimalsController {
  constructor(private animalsService: AnimalsService) { }

  @ApiOperation({
    summary: "Register a new animal in the database."
  })
  @ApiResponse({ status: 201, description: "The new registered animal returns" })
  @Post()
  @ApiBearerAuth("jwt")
  @UsePipes(ValidationPipe)
  createAnimal(@Body() createAnimalDto: CreateAnimalDto): Promise<Animal> {
    return this.animalsService.createAnimal(createAnimalDto);
  }

  @ApiOperation({ summary: "Returns the list with all registered animals" })
  @ApiResponse({ status: 200, description: "Return to animal list" })
  @Get()
  @ApiBearerAuth("jwt")
  getAnimals(): Promise<Animal[]> {
    console.log("request");
    return this.animalsService.getAnimals();
  }

  @ApiOperation({
    summary:
      "Returns the list with all animals registered by the user with a given id."
  })
  @ApiResponse({
    status: 200,
    description: "Returns the list of animals from the given user"
  })
  @Get("/user/:userId")
  @ApiBearerAuth("jwt")
  getUserAnimals(
    @Param("userId", new ParseUUIDPipe()) userId: string
  ): Promise<Animal[]> {
    return this.animalsService.getUserAnimals(userId);
  }

  @ApiOperation({ summary: "Returns the animal with the given id." })
  @ApiParam({ name: "id", description: "animal id." })
  @ApiResponse({
    status: 200,
    description: "Returns the animal that has the id."
  })
  @Get(":id")
  @ApiBearerAuth("jwt")
  getAnimalById(@Param("id", new ParseUUIDPipe()) id: string): Promise<Animal> {
    return this.animalsService.getAnimalById(id);
  }

  @ApiOperation({ summary: "Deletes an animal from the database." })
  @ApiParam({ name: "id", description: "animal id." })
  @ApiResponse({
    status: 200,
    description: "The animal with the given id was excluded."
  })
  @Delete(":id")
  @ApiBearerAuth("jwt")
  deleteAnimal(@Param("id", new ParseUUIDPipe()) id: string): Promise<void> {
    return this.animalsService.deleteAnimal(id);
  }
}
