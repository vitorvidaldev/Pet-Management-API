import { Entity, BaseEntity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";
import { Animal } from "src/animals/animal.entity";

@Entity('vacina')
export class Vaccine extends BaseEntity {

    @PrimaryGeneratedColumn('uuid', { name: 'id_vacina' })
    id: number;

    @Column({ name: 'especie_animal' })
    species: string;

    @Column({ name: 'raca' })
    race: string;

    @Column({ name: 'nome_vacina' })
    name: string;

    @Column({ name: 'meses_apos_nascimento' })
    monthsAfterBirth: number;

    @Column()
    animalId: number;

    @ManyToOne(() => Animal, animal => animal.vaccines, { onDelete: "CASCADE" })
    @JoinColumn({ name: 'animal_id_animal' })
    animal: Animal;
}