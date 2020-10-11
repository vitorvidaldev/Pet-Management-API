import { Entity, BaseEntity, PrimaryGeneratedColumn, Column, ManyToOne, CreateDateColumn, JoinColumn } from "typeorm";
import { Animal } from "src/animals/animal.entity";

@Entity('notificacao')
export class Notification extends BaseEntity {

    @PrimaryGeneratedColumn('uuid', { name: 'id_notificacao' })
    id: string;

    @Column({ name: 'titulo' })
    title: string;

    @Column({ name: 'descricao' })
    description: string;

    @CreateDateColumn({ name: 'data_criacao' })
    creationDate: string;

    @Column({ name: 'data_notificacao' })
    notificationDate: string;

    @Column({ name: 'frequencia_de_notificacao' })
    frequency: string;

    @ManyToOne(() => Animal, animal => animal.notifications, { onDelete: "CASCADE" })
    @JoinColumn({ name: 'animal_id_animal' })
    animal: Animal;
}