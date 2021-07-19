import {
  Controller,
  Post,
  UsePipes,
  ValidationPipe,
  Body,
  Get,
  Param,
  Delete,
  ParseUUIDPipe,
  UseGuards
} from "@nestjs/common";
import { NoteService } from "./note.service";
import { CreateNoteDto } from "./dto/create-note.dto";
import { Note } from "./note.entity";
import {
  ApiBearerAuth,
  ApiOperation,
  ApiParam,
  ApiResponse,
  ApiTags
} from "@nestjs/swagger";
import { AuthGuard } from "@nestjs/passport";

@ApiTags("Note")
@ApiResponse({ status: 401, description: "Unauthorized" })
@Controller("note")
@UseGuards(AuthGuard())
export class NoteController {
  constructor(private noteService: NoteService) {}

  @ApiOperation({
    summary: "Create a new note for the pet that has a given id."
  })
  @ApiResponse({
    status: 201,
    description: "Create a new note for the pet with a given id."
  })
  @Post()
  @ApiBearerAuth("jwt")
  @UsePipes(ValidationPipe)
  save(@Body() dto: CreateNoteDto): Promise<Note> {
    return this.noteService.save(dto);
  }

  @ApiOperation({ summary: "Return all grades for a given pet" })
  @ApiParam({ name: "id", description: "id" })
  @ApiResponse({ status: 200, description: "Returns a list of notes" })
  @Get("pet/:id")
  @ApiBearerAuth("jwt")
  findAll(@Param("id", new ParseUUIDPipe()) id: string): Promise<Note[]> {
    return this.noteService.findAll(id);
  }

  @ApiOperation({ summary: "Returns a note that has a given id" })
  @ApiParam({ name: "id", description: "Note id" })
  @ApiResponse({
    status: 200,
    description: "Returns a note that has a given id"
  })
  @Get(":id")
  @ApiBearerAuth("jwt")
  findById(@Param("id", new ParseUUIDPipe()) id: string): Promise<Note> {
    return this.noteService.findById(id);
  }

  @ApiOperation({ summary: "Deletes a note that has a given id" })
  @ApiParam({ name: "id", description: "Note id" })
  @ApiResponse({ status: 200, description: "Returns void" })
  @Delete(":id")
  @ApiBearerAuth("jwt")
  deleteById(@Param("id", new ParseUUIDPipe()) id: string): Promise<void> {
    return this.noteService.deleteById(id);
  }
}
