import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  OneToMany,
  JoinColumn
} from "typeorm";
import { User } from "src/users/entity/user.entity";
import { Vaccine } from "src/vaccines/vaccine.entity";
import { Note } from "src/notes/notes.entity";

@Entity("animal")
export class Animal extends BaseEntity {
  @PrimaryGeneratedColumn("uuid", { name: "id_animal" })
  id!: string;

  @Column({ name: "nome" })
  name!: string;

  @Column({ name: "data_nascimento", type: "timestamp" })
  birthDate!: string;

  @Column({ name: "especie_animal" })
  species!: string;

  @Column({ name: "raca" })
  breed!: string;

  @ManyToOne(
    () => User,
    user => user.animals,
    { onDelete: "CASCADE" }
  )
  @JoinColumn({ name: "usuario_id_usuario" })
  user!: User;

  @OneToMany(
    () => Vaccine,
    vaccine => vaccine.animal
  )
  vaccines!: Vaccine[];

  @OneToMany(
    () => Note,
    note => note.animal
  )
  notes!: Note[];
}
