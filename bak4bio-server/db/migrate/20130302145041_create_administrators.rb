class CreateAdministrators < ActiveRecord::Migration
  def change
    create_table :administrators do |t|
      t.integer :user_id
      t.timestamps
    end
    add_index(:administrators, :user_id)
  end
end
