import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FuncionarioComponent } from './funcionario-component';

describe('FuncionarioComponent', () => {
  let component: FuncionarioComponent;
  let fixture: ComponentFixture<FuncionarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuncionarioComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FuncionarioComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
