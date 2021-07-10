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
import { NotesService } from "./notes.service";
import { CreateNoteDto } from "./dto/create-note.dto";
import { Note } from "./notes.entity";
import {
  ApiBearerAuth,
  ApiOperation,
  ApiParam,
  ApiResponse,
  ApiTags
} from "@nestjs/swagger";
import { AuthGuard } from "@nestjs/passport";

@ApiTags("Notes")
@ApiResponse({ status: 401, description: "Unauthorized" })
@Controller("notes")
@UseGuards(AuthGuard())
export class NotesController {
  constructor(private notesService: NotesService) { }

  @ApiOperation({ summary: "Create a new note for the animal that has a given id." })
  @ApiResponse({
    status: 201,
    description: "Create a new note for the animal with a given id."
  })
  @Post()
  @ApiBearerAuth("jwt")
  @UsePipes(ValidationPipe)
  createNote(@Body() createnoteDto: CreateNoteDto): Promise<Note> {
    return this.notesService.createNote(createnoteDto);
  }

  @ApiOperation({ summary: "Return all grades for a given animal" })
  @ApiParam({ name: "animalId", description: "animal id" })
  @ApiResponse({ status: 200, description: "Returns a list of notes" })
  @Get("animal/:animalId")
  @ApiBearerAuth("jwt")
  getNotes(
    @Param("animalId", new ParseUUIDPipe()) id: string
  ): Promise<Note[]> {
    return this.notesService.getNotes(id);
  }

  @ApiOperation({ summary: "Returns a note that has a given id" })
  @ApiParam({ name: "id", description: "Note id" })
  @ApiResponse({ status: 200, description: "Returns a note that has a given id" })
  @Get(":id")
  @ApiBearerAuth("jwt")
  getNoteById(@Param("id", new ParseUUIDPipe()) id: string): Promise<Note> {
    return this.notesService.getNoteById(id);
  }

  @ApiOperation({ summary: "Deletes a note that has a given id" })
  @ApiParam({ name: "id", description: "Note id" })
  @ApiResponse({ status: 200, description: "Returns void" })
  @Delete(":id")
  @ApiBearerAuth("jwt")
  deleteNote(@Param("id", new ParseUUIDPipe()) id: string): Promise<void> {
    return this.notesService.deleteNote(id);
  }
}
