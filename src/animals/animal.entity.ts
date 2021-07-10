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

  @Column({ name: "name" })
  name!: string;

  @Column({ name: "birthdate", type: "timestamp" })
  birthDate!: string;

  @Column({ name: "species" })
  species!: string;

  @Column({ name: "breed" })
  breed!: string;

  @ManyToOne(
    () => User,
    user => user.animals,
    { onDelete: "CASCADE" }
  )
  @JoinColumn({ name: "user_id_user" })
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
