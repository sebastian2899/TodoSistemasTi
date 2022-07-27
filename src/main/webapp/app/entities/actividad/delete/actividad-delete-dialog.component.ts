import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IActividad } from '../actividad.model';
import { ActividadService } from '../service/actividad.service';

@Component({
  templateUrl: './actividad-delete-dialog.component.html',
})
export class ActividadDeleteDialogComponent {
  actividad?: IActividad;

  constructor(protected actividadService: ActividadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actividadService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
