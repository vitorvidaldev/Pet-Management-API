import { PipeTransform, BadRequestException } from "@nestjs/common";
import { NoteFrequency } from "../note-frequency.enum";

export class NoteFrequencyValidationPipe implements PipeTransform {
    readonly allowedNotifications = [
        NoteFrequency.DAY,
        NoteFrequency.WEEK,
        NoteFrequency.MONTH,
        NoteFrequency.NONE,
    ];

    transform(value: any) {
        value = value.toUpperCase();

        if (!this.isNotificationValid(value)) {
            throw new BadRequestException(value + ' não é uma frequência válida.');
        }

        return value;
    }

    private isNotificationValid(frequency: any) {
        const idx = this.allowedNotifications.indexOf(frequency);
        return idx !== -1;
    }
}