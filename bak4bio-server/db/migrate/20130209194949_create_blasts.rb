class CreateBlasts < ActiveRecord::Migration
  def change
    create_table :blasts do |t|
      t.integer :owner_id
      t.string :title
      t.integer :entry_id
      t.datetime :start_at
      t.string :status
      t.datetime :end_at
      t.integer :output_id
      t.string :program
      t.string :database
      t.integer :word_size
      t.integer :max_target_sequence
      t.decimal :expect
      t.decimal :max_matches_range
      t.string :m_scores
      t.string :gap_costs

      t.timestamps
    end
    add_index(:blasts, :owner_id)
    add_index(:blasts, :entry_id)
    add_index(:blasts, :output_id)
  end
end
