import {
  Body,
  Controller,
  Get,
  Post,
  Query,
  UseGuards,
  ValidationPipe,
} from "@nestjs/common";
import { AuthGuard } from "@nestjs/passport";
import {
  ApiBearerAuth,
  ApiOperation,
  ApiResponse,
  ApiTags,
} from "@nestjs/swagger";
import { CreateVaccineDto } from "./dto/create-vaccine.dto";
import { GetVaccinesDto } from "./dto/get-vaccines.dto";
import { Vaccine } from "./vaccine.entity";
import { VaccinesService } from "./vaccines.service";

@ApiTags("Vacinas")
@ApiResponse({ status: 401, description: "Usuário não autorizado" })
@Controller("vaccines")
@UseGuards(AuthGuard())
export class VaccinesController {
  constructor(private vaccinesService: VaccinesService) {}

  // TODO: Fix it later. Changed because of testing
  @ApiOperation({ summary: "Retorna as vacinas cadastradas" })
  @ApiResponse({
    status: 200,
    description: "Lista de vacinas cadastradas, de acordo com os parâmentros",
  })
  @Get()
  @ApiBearerAuth("jwt")
  getVaccines(): // @Query(ValidationPipe) filterDto: GetVaccinesDto
  Promise<Vaccine[]> {
    // return this.vaccinesService.getVaccines(filterDto);
    return this.vaccinesService.getVaccines();
  }

  @ApiOperation({ summary: "Cadastro de novas vacinas no banco de dados" })
  @ApiResponse({ status: 201, description: "Vacina cadastrada com sucesso." })
  @Post()
  @ApiBearerAuth("jwt")
  createVaccine(
    @Body(ValidationPipe) createVaccineDto: CreateVaccineDto
  ): Promise<Vaccine> {
    return this.vaccinesService.createVaccine(createVaccineDto);
  }
}
