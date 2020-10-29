import { Entity, BaseEntity, PrimaryGeneratedColumn, Column, ManyToOne, OneToMany, JoinColumn } from "typeorm";
import { User } from "src/users/user.entity";
import { Vaccine } from "src/vaccines/vaccine.entity";
import { Note } from "src/notes/note.entity";
import { AnimalSpecies } from "./enum/animal-species.enum";

@Entity('animal')
export class Animal extends BaseEntity {
    @PrimaryGeneratedColumn('uuid', { name: 'id_animal' })
    id: string;

    @Column({ name: 'nome' })
    name: string;

    @Column({ name: 'data_nascimento' })
    birthDate: string;

    @Column({ name: 'especie_animal' })
    species: AnimalSpecies;

    @Column({ name: 'raca' })
    race: string;

    @ManyToOne(() => User, user => user.animals, { onDelete: "CASCADE" })
    @JoinColumn({ name: 'usuario_id_usuario' })
    user: User;

    @OneToMany(() => Vaccine, vaccine => vaccine.animal)
    vaccines: Vaccine[];

    @OneToMany(() => Note, note => note.animal)
    notes: Note[];
}