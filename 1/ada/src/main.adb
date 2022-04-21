with Text_IO; use Text_IO;
with Ada.Numerics.Discrete_Random;

procedure Main is
   type randRange is new Integer range 1..100;
   package Rand_Int is new ada.numerics.discrete_random(randRange);
   use Rand_Int;
   gen: Generator;
   task_id: Integer := 1;

   task type main_thread;
   task type sum_thread(id: Integer);

   task body sum_thread is
      sum: Integer := 0;
      count: Integer := 0;
      end_index: randRange;
   begin
      reset(gen);
      end_index := random(gen);
      for i in 1..end_index
      loop
         sum := sum + Integer(i);
         count := count + 1;
      end loop;
      put_line("task id: " & Integer'Image(id) & ";  Result sum: " & Integer'Image(sum) & "; Addition count: " & Integer'Image(count));
   end sum_thread;

   task body main_thread is
      s1: sum_thread(id => 1);
      s2: sum_thread(id => 2);
      s3: sum_thread(id => 3);
      s4: sum_thread(id => 4);
      s5: sum_thread(id => 5);
   begin
      null;
   end main_thread;

   m: main_thread;

begin
   --  Insert code here.
   null;
end Main;
