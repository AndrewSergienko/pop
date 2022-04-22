with Ada.Numerics.Discrete_Random;
with Ada.Text_IO; use Ada.Text_IO;

procedure Main is
   type randNum is new Integer range -100000..100000;
   type numsArr is array (0..1000) of Integer;
   type boolArr is array (0..9) of Boolean;

   package Rand_Num is new ada.numerics.discrete_random(randNum);

   gen_num: Rand_Num.Generator;

   global_min: Integer := Integer'Last;
   nums: numsArr := (others => 0);
   arr_create: Boolean := False;
   threads_finished_arr: boolArr := (others => False);

   task type main_thread;
   task type find_min(index: Integer);
   task type set_min_value_thread is
      entry set_value(value: Integer);
      entry stop;
   end set_min_value_thread;

   task body set_min_value_thread is
   begin
      loop
         select
            accept set_value (value : Integer) do
               if value < global_min then
                  global_min := value;
               end if;
            end set_value;
            or
            accept stop;
            exit ;
         end select;
      end loop;
   end set_min_value_thread;

   s: set_min_value_thread;

   task body find_min is
      min: Integer := Integer'Last;
   begin
      while not arr_create
      loop
         null;
      end loop;
      for i in index*100..(index*100)+100
      loop
         if nums(i) < min then
            min := nums(i);
         end if;
      end loop;
      s.set_value(value => min);
      threads_finished_arr(index) := True;
   end find_min;

   task body main_thread is
      f0: find_min(index => 0);
      f1: find_min(index => 1);
      f2: find_min(index => 2);
      f3: find_min(index => 3);
      f4: find_min(index => 4);
      f5: find_min(index => 5);
      f6: find_min(index => 6);
      f7: find_min(index => 7);
      f8: find_min(index => 8);
      f9: find_min(index => 9);
      threads_finished: Boolean := False;
   begin
      Rand_Num.reset(gen_num);
      for i in 0..1000
      loop
         nums(i) := Integer(Rand_Num.Random(gen_num));
      end loop;
      arr_create := True;
      while not threads_finished
      loop
         threads_finished := True;
         for i in 0..9 loop
            if not threads_finished_arr(i) then
               threads_finished := False;
            end if;
         end loop;
      end loop;
      s.stop;
      Put_Line("Result: " & Integer'Image(global_min));
   end main_thread;

   main: main_thread;
begin
   --  Insert code here.
   null;
end Main;
