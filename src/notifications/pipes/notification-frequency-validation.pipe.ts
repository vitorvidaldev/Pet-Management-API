import { PipeTransform, BadRequestException } from "@nestjs/common";
import { NotificationFrequency } from "../notification-frequency.enum";

export class NotificationFrequencyValidationPipe implements PipeTransform {
    readonly allowedNotifications = [
        NotificationFrequency.DAY,
        NotificationFrequency.WEEK,
        NotificationFrequency.MONTH,
        NotificationFrequency.NONE,
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