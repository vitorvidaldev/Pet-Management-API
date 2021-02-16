import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  CreateDateColumn,
  JoinColumn,
} from "typeorm";
import { Animal } from "src/animals/animal.entity";

@Entity("nota")
export class Note extends BaseEntity {
  @PrimaryGeneratedColumn("uuid", { name: "id_nota" })
  id!: string;

  @Column({ name: "tipo" })
  noteType!: string;

  @Column({ name: "titulo" })
  title!: string;

  @Column({ name: "descricao" })
  description!: string;

  @CreateDateColumn({ name: "data_criacao" })
  creationDate!: string;

  @Column({ name: "data_nota" })
  noteDate!: Date;

  @Column({ name: "frequencia_de_nota" })
  frequency!: string;

  // "Cascade" obriga a exclusão de dados conectados
  // "Cascade" obriga a exclusão de dados conectados

  @ManyToOne(
    () => Animal,
    (animal) => animal.notes,
    { onDelete: "CASCADE" }
  )
  @JoinColumn({ name: "animal_id_animal" })
  animal!: Animal;
}
