/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DepartamentoDetailComponent } from '../../../../../../main/webapp/app/entities/departamento/departamento-detail.component';
import { DepartamentoService } from '../../../../../../main/webapp/app/entities/departamento/departamento.service';
import { Departamento } from '../../../../../../main/webapp/app/entities/departamento/departamento.model';

describe('Component Tests', () => {

    describe('Departamento Management Detail Component', () => {
        let comp: DepartamentoDetailComponent;
        let fixture: ComponentFixture<DepartamentoDetailComponent>;
        let service: DepartamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [DepartamentoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DepartamentoService,
                    JhiEventManager
                ]
            }).overrideTemplate(DepartamentoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepartamentoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Departamento(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.departamento).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
