import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  CreateDateColumn,
  JoinColumn
} from "typeorm";
import { Animal } from "src/animals/animal.entity";

@Entity("note")
export class Note extends BaseEntity {
  @PrimaryGeneratedColumn("uuid", { name: "id_note" })
  id!: string;

  @Column({ name: "type" })
  noteType!: string;

  @Column({ name: "title" })
  title!: string;

  @Column({ name: "description" })
  description!: string;

  @CreateDateColumn({ name: "creation_date" })
  creationDate!: string;

  @Column({ name: "trigger_date" })
  noteDate!: Date;

  @Column({ name: "frequency" })
  frequency!: string;

  @ManyToOne(
    () => Animal,
    animal => animal.notes,
    { onDelete: "CASCADE" }
  )
  @JoinColumn({ name: "animal_id_animal" })
  animal!: Animal;
}
