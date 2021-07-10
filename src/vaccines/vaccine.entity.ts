import {
  Entity,
  BaseEntity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn
} from "typeorm";
import { Animal } from "src/animals/animal.entity";

@Entity("vacina")
export class Vaccine extends BaseEntity {
  @PrimaryGeneratedColumn("uuid", { name: "id_vacina" })
  id?: number;

  @Column({ name: "nome_vacina" })
  name!: string;

  @Column({ name: "especie_animal" })
  species!: string;

  @Column({ name: "raca" })
  breed!: string;

  @Column({ name: "meses_apos_nascimento" })
  monthsAfterBirth!: string;

  @ManyToOne(
    () => Animal,
    animal => animal.vaccines,
    { onDelete: "CASCADE" }
  )
  @JoinColumn({ name: "animal_id_animal" })
  animal?: Animal;
}
