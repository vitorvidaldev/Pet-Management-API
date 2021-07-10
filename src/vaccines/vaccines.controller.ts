import {
  Body,
  Controller,
  Get,
  Post,
  UseGuards,
  ValidationPipe
} from "@nestjs/common";
import { AuthGuard } from "@nestjs/passport";
import {
  ApiBearerAuth,
  ApiOperation,
  ApiResponse,
  ApiTags
} from "@nestjs/swagger";
import { CreateVaccineDto } from "./dto/create-vaccine.dto";
import { Vaccine } from "./vaccine.entity";
import { VaccinesService } from "./vaccines.service";

@ApiTags("Vaccines")
@ApiResponse({ status: 401, description: "Unauthorized" })
@Controller("vaccines")
@UseGuards(AuthGuard())
export class VaccinesController {
  constructor(private vaccinesService: VaccinesService) { }

  // TODO: Add filter parameter
  @ApiOperation({ summary: "Returns vaccines available" })
  @ApiResponse({
    status: 200,
    description: "Lists all indexed vaccines"
  })
  @Get()
  @ApiBearerAuth("jwt")
  getVaccines(): Promise<Vaccine[]> {
    return this.vaccinesService.getVaccines();
  }

  @ApiOperation({ summary: "Registration of new vaccines in the database" })
  @ApiResponse({ status: 201, description: "Vaccine successfully registered." })
  @Post()
  @ApiBearerAuth("jwt")
  createVaccine(
    @Body(ValidationPipe) createVaccineDto: CreateVaccineDto
  ): Promise<Vaccine> {
    return this.vaccinesService.createVaccine(createVaccineDto);
  }
}
