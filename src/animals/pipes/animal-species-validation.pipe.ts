import { PipeTransform, BadRequestException } from "@nestjs/common";
import { AnimalSpecies } from "../animal-species.enum";

export class AnimalSpeciesValidationPipe implements PipeTransform {
    readonly allowedSpecies = [
        AnimalSpecies.CACHORRO,
        AnimalSpecies.GATO,
        AnimalSpecies.NAOINFORMAR,
        AnimalSpecies.OUTRO,
    ];

    transform(value: any) {
        value = value.toUpperCase();

        if (!this.isSpeciesValid(value)) {
            throw new BadRequestException(value + ' não é uma espécie válida.');
        }

        return value;
    }

    private isSpeciesValid(status: any) {
        const idx = this.allowedSpecies.indexOf(status);
        return idx !== -1;
    }
}