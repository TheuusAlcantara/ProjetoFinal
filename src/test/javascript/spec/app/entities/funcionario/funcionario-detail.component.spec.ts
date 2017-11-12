/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FuncionarioDetailComponent } from '../../../../../../main/webapp/app/entities/funcionario/funcionario-detail.component';
import { FuncionarioService } from '../../../../../../main/webapp/app/entities/funcionario/funcionario.service';
import { Funcionario } from '../../../../../../main/webapp/app/entities/funcionario/funcionario.model';

describe('Component Tests', () => {

    describe('Funcionario Management Detail Component', () => {
        let comp: FuncionarioDetailComponent;
        let fixture: ComponentFixture<FuncionarioDetailComponent>;
        let service: FuncionarioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [FuncionarioDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FuncionarioService,
                    JhiEventManager
                ]
            }).overrideTemplate(FuncionarioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FuncionarioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FuncionarioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Funcionario(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.funcionario).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
